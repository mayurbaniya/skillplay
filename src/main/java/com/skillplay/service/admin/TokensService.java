package com.skillplay.service.admin;

import com.skillplay.dto.TokensDto;
import com.skillplay.entity.user.Tokens;
import com.skillplay.entity.user.User;
import com.skillplay.repository.TokensRepository;
import com.skillplay.repository.UserRepository;
import com.skillplay.utils.GlobalResponse;
import com.skillplay.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokensService {

    private final UserRepository userRepository;
    private final TokensRepository tokensRepository;
    private final ModelMapper modelMapper;


    public GlobalResponse addToken(TokensDto tokensDto){

        long userID = tokensDto.getUserID();

        Optional<User> byId = userRepository.findById(userID);

        if(byId.isPresent()){
            User user = byId.get();
            Optional<Tokens> tokensOptional = tokensRepository.findByUserID(user);

            if(tokensOptional.isPresent()){
                Tokens tokens = tokensOptional.get();
                tokens.setToken(tokens.getToken() + tokensDto.getToken());
                Tokens save = tokensRepository.save(tokens);

                return GlobalResponse.builder()
                        .msg("User tokens Updated")
                        .status(AppConstants.UPDATED)
                        .data("total tokens: "+save.getToken())
                        .build();

            }else {
                Tokens tokens = modelMapper.map(tokensDto, Tokens.class);
                tokens.setUserID(user);
                tokensRepository.save(tokens);

                return GlobalResponse.builder()
                        .msg("User tokens Added")
                        .status(AppConstants.ADDED)
                        .data("total tokens: "+ tokensDto.getToken())
                        .build();
            }
        }else {

            return GlobalResponse.builder()
                    .msg("User not found")
                    .status(AppConstants.USER_NOT_FOUND)
                    .build();
        }



    }
}
