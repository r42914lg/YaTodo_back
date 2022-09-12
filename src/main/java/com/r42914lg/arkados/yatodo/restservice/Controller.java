package com.r42914lg.arkados.yatodo.restservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.r42914lg.arkados.yatodo.repository.DbRepo;
import com.r42914lg.arkados.yatodo.repository.IRepo;
import com.r42914lg.arkados.yatodo.repository.RepoFactory;

@RestController
public class Controller {

	private static final String FIREBASE_JSON_PATH =
		"d:/DEV/Spring/YaTodo-Firebase-JSON/yatodo-d6c8e-firebase-adminsdk-ewns2-c21cab5146.json"; 

	@Autowired
	RepoFactory factory;

	public Controller() {
		try {

			FileInputStream serviceAccount = 
				new FileInputStream(FIREBASE_JSON_PATH);

			FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();
			
			FirebaseApp.initializeApp(options);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PatchMapping("/items")
	public ResponseEntity<List<TodoItem>> updateTodoItems(
		@RequestHeader (name="Authorization") String idToken,
		@RequestBody List<TodoItem> listFromFront) {

		FirebaseToken decodedToken = null;

		try {
			decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken.replace("Bearer ", ""));
		} catch (FirebaseAuthException e) {
			if (e.getAuthErrorCode() == AuthErrorCode.EXPIRED_ID_TOKEN)
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			else {
				e.printStackTrace();
			}
		}

		printListItems(listFromFront, "List from front");
		
		IRepo repository = factory.getRepo(DbRepo.class);
		
		String userid = decodedToken.getUid();
		List<TodoItem> listFromBack = repository.getTodoItems(userid);
		printListItems(listFromBack, "List from repository");

		List<TodoItem> mergedList = merge(listFromFront, listFromBack);
		printListItems(mergedList, "Megred list");

		repository.clearUserItems(userid);
		repository.addTodoItems(mergedList, userid);

		List<TodoItem> toReturn = repository.getTodoItems(userid);
		printListItems(toReturn, "To return list from repository");

		return ResponseEntity.ok(toReturn);
	}

	private List<TodoItem> merge(List<TodoItem> frontList, List<TodoItem> repoList) {

		for (TodoItem backItem : repoList)
			for (TodoItem frontItem : frontList)
				if (frontItem.getLocalid() == backItem.getLocalid() 
						&& frontItem.getChanged() > backItem.getChanged())
					backItem.updateFrom(frontItem);
		

		for (TodoItem frontItem : frontList)
			if (!repoList.contains(frontItem))
				repoList.add(frontItem);
		

		Iterator<TodoItem> iter = repoList.iterator();
		while(iter.hasNext()) {
			if (iter.next().getDeletepending())
				iter.remove();
		}

		return repoList;
	}

	private <T> void printListItems(List<T> list, String TAG) {
		System.out.print(TAG + " \n\n[");
		for (T t : list)
			System.out.print(t);
		System.out.print("]\n\n");
	}
}
