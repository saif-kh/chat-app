package com.example.chatback.Services;

import com.example.chatback.Dtos.ChatRoomDto;
import com.example.chatback.Dtos.MessageDto;
import com.example.chatback.Entities.ChatRoom;
import com.example.chatback.Entities.Message;
import com.example.chatback.Entities.User;
import com.example.chatback.Repositories.ChatRoomRepository;
import com.example.chatback.Repositories.MessageRepository;
import com.example.chatback.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@Getter
@Setter
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final MessageService messageService;

    public List<ChatRoom> findAllByUserId(Long userId) {
        return chatRoomRepository.findChatRoomsByFirst_user_idOrSecond_user_id(userId) ;
    }

    public Optional<ChatRoom> findByID(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId) ;
    }

    public List<ChatRoomDto> getChatRoomOfTheUsers(Long userId) {
        List<ChatRoom> chatRooms = findAllByUserId(userId);
        List<ChatRoomDto> chatRoomDtos = new ArrayList<>();

        for (ChatRoom chatRoom : chatRooms) {
            User myFriend = null;

            if (userId.equals(chatRoom.getFirstUserId())) {
                // Im the first id of the chat room my friend is the 2nd id
                myFriend = userRepository.findById(chatRoom.getSecondUserId()).orElse(null);
            } else if (userId.equals(chatRoom.getSecondUserId())) {
                // IIm the second id of the chat room my friend is the 1st id
                myFriend = userRepository.findById(chatRoom.getFirstUserId()).orElse(null);
            }
            if (myFriend != null) {
                chatRoomDtos.add(new ChatRoomDto(myFriend.getUsername(), chatRoom.getId()));
            }
        }
        return chatRoomDtos;
    }

    public ChatRoom saveChatRoom(Long firstId , Long secondId) throws Exception {
        ChatRoom chatRoom = new ChatRoom() ;
        chatRoom.setFirstUserId(firstId);
        chatRoom.setSecondUserId(secondId);
        return chatRoomRepository.save(chatRoom) ;
    }

    public boolean updateChatRoomByMessage(Long chatRoomId, MessageDto newMessage)  {
            try {
                ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(Exception::new);
                Message message = messageService.fromDtoToEntity(newMessage);
                message.setChatRoom(chatRoom);
                messageService.save(message);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

    public ChatRoom saveChatRoom(String username1, String username2) {

        log.debug(username2);

        System.out.println( "dcdcdcdcdcdc " + username2);

      User user1 =  userRepository.findUserByUserName(username1).orElse(null) ;

      User user2 =  userRepository.findUserByUserName(username2).orElse(null) ;

        ChatRoom chatRoom = new ChatRoom() ;

        assert user1 != null;
        chatRoom.setFirstUserId(user1.getId());
        assert user2 != null;
        chatRoom.setSecondUserId(user2.getId());
        return chatRoomRepository.save(chatRoom) ;
    }
}

