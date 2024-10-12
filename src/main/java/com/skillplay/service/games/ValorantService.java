package com.skillplay.service.games;

import com.skillplay.dto.ValorantDTO;
import com.skillplay.entity.games.Valorant;
import com.skillplay.repository.ValorantRepository;
import com.skillplay.utils.GlobalResponse;
import com.skillplay.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValorantService {

    private static final Logger log = LoggerFactory.getLogger(ValorantService.class);
    private final ValorantRepository valorantRepository;


    public GlobalResponse createValorantGame(ValorantDTO dto) {
        try {
          //  Valorant valorant = valorantMapper.toEntity(dto);
           // valorant = valorantRepository.save(valorant);

            return GlobalResponse.builder()
                    .msg("Match Created")

                    .status(AppConstants.SUCCESS)
                    .build();

        }catch (Exception e){
            return GlobalResponse.builder()
                    .msg("Something went wrong while creating Valorant match")
                    .status(AppConstants.ERROR)
                    .build();
        }


    }

    public GlobalResponse getValorantGame(Long id) {
        try {
            Valorant valorant = valorantRepository.findById(id).orElse(null);

            if(valorant != null){
                return GlobalResponse.builder()
                        .msg("Match found")

                        .status(AppConstants.SUCCESS)
                        .build();
            }else {
                return GlobalResponse.builder()
                        .msg("Match not found")
                        .status(AppConstants.FAILED)
                        .build();
            }


        }catch (Exception e){
            log.error("error fetching valorant match :{}",e);
            return GlobalResponse.builder()
                    .msg("Something went wrong while creating Valorant match")
                    .status(AppConstants.ERROR)
                    .build();
        }

    }

}
