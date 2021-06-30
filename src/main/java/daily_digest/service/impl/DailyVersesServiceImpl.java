package daily_digest.service.impl;

import daily_digest.domain.DailyVerses;
import daily_digest.repository.DailyVersesRepository;
import daily_digest.service.DailyVersesService;
import daily_digest.service.dto.DailyVersesDTO;
import daily_digest.service.mapper.DailyVersesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DailyVerses}.
 */
@Service
@Transactional
public class DailyVersesServiceImpl implements DailyVersesService {

    private final Logger log = LoggerFactory.getLogger(DailyVersesServiceImpl.class);

    private final DailyVersesRepository dailyVersesRepository;

    private final DailyVersesMapper dailyVersesMapper;

    public DailyVersesServiceImpl(DailyVersesRepository dailyVersesRepository, DailyVersesMapper dailyVersesMapper) {
        this.dailyVersesRepository = dailyVersesRepository;
        this.dailyVersesMapper = dailyVersesMapper;
    }

    @Override
    public DailyVersesDTO save(DailyVersesDTO dailyVersesDTO) {
        log.debug("Request to save DailyVerses : {}", dailyVersesDTO);
        DailyVerses dailyVerses = dailyVersesMapper.toEntity(dailyVersesDTO);
        dailyVerses = dailyVersesRepository.save(dailyVerses);
        return dailyVersesMapper.toDto(dailyVerses);
    }

    @Override
    public Optional<DailyVersesDTO> partialUpdate(DailyVersesDTO dailyVersesDTO) {
        log.debug("Request to partially update DailyVerses : {}", dailyVersesDTO);

        return dailyVersesRepository
            .findById(dailyVersesDTO.getId())
            .map(
                existingDailyVerses -> {
                    dailyVersesMapper.partialUpdate(existingDailyVerses, dailyVersesDTO);

                    return existingDailyVerses;
                }
            )
            .map(dailyVersesRepository::save)
            .map(dailyVersesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DailyVersesDTO> findAll() {
        log.debug("Request to get all DailyVerses");
        return dailyVersesRepository.findAll().stream().map(dailyVersesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DailyVersesDTO> findOne(Long id) {
        log.debug("Request to get DailyVerses : {}", id);
        return dailyVersesRepository.findById(id).map(dailyVersesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DailyVerses : {}", id);
        dailyVersesRepository.deleteById(id);
    }
}
