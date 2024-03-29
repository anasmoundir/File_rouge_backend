package com.example.demo.Mapper;
import  com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.dto.SignUpDTO;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface userMapper {
    UserDTO userToUserDTO(User user);
     User userDTOToUser(UserDTO userDTO);

     User signUpDTOToUser(SignUpDTO signUpDTO);
}
