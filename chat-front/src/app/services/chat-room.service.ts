import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable } from 'rxjs';
import { ChatRoom } from '../models/chatRoom.type';
import { CREAT_CHATROOM_URL, GET_CHATROOMS_URL, GET_MESSAGES_URL } from '../constants/apis';
import { Message } from '../models/message.type';

@Injectable({
  providedIn: 'root'
})
export class ChatRoomService {

  constructor(private http: HttpClient) { }

  idChatRoom: string | undefined ;

  getFriends(userId : string) : Observable<Array<ChatRoom>> {
    return this.http.get<Array<ChatRoom>>(GET_CHATROOMS_URL + "/" + userId).pipe(
      map((res) => {
        return res;
      }),
      catchError((err: HttpErrorResponse) =>{
          throw err;
        })
    )
  }


  getMessages(chatRoomId : string) : Observable<Array<Message>> {
    return this.http.get<Array<Message>>(GET_MESSAGES_URL + "/" + chatRoomId).pipe(
      map((res) => {
        return res;
      }),
      catchError((err: HttpErrorResponse) =>{
          throw err;
        })
    )
  }


  saveChatRoom(username1 : string , username2 : string) : Observable<Array<Message>> {
    return this.http.post<any>(CREAT_CHATROOM_URL  +  `?username1=${username1}&username2=${username2}`  , null).pipe(
      map((res) => {
        return res;
      }),
      catchError((err: HttpErrorResponse) =>{
          throw err;
        })
    )
  }

  setIdChatRoom(id: string) {
    this.idChatRoom = id;
  }


  getIdChatRoom() {
   return this.idChatRoom;
  }

}
