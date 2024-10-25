package com.skillplay.service.games;


import com.skillplay.dto.BGMIDto;
import com.skillplay.entity.games.BGMI;
import com.skillplay.repository.BgmiRepository;
import com.skillplay.utils.AppConstants;
import com.skillplay.utils.GlobalResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BgmiService {

    private final BgmiRepository bgmiRepository;


    public GlobalResponse createMatch(BGMIDto bgmiDto){
       return null;
    }

    public GlobalResponse getMatchById(int matchID){
        return null;
    }


    public GlobalResponse getAllMatches(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "datePlayed"));
        Page<BGMI> matchesPage = bgmiRepository.findAll(pageable);

        return GlobalResponse.builder()
                .msg("All matches list")
                .status(AppConstants.SUCCESS)
                .data(matchesPage.getContent())
                .build();
    }

}