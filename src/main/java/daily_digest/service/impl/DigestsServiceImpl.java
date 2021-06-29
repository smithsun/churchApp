package daily_digest.service.impl;

import daily_digest.domain.Digests;
import daily_digest.repository.DigestsRepository;
import daily_digest.service.DigestsService;
import daily_digest.service.dto.DigestsDTO;
import daily_digest.service.mapper.DigestsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Digests}.
 */
@Service
@Transactional
public class DigestsServiceImpl implements DigestsService {

    private final Logger log = LoggerFactory.getLogger(DigestsServiceImpl.class);

    private final DigestsRepository digestsRepository;

    private final DigestsMapper digestsMapper;

    public DigestsServiceImpl(DigestsRepository digestsRepository, DigestsMapper digestsMapper) {
        this.digestsRepository = digestsRepository;
        this.digestsMapper = digestsMapper;
    }

    @Override
    public DigestsDTO save(DigestsDTO digestsDTO) {
        log.debug("Request to save Digests : {}", digestsDTO);
        Digests digests = digestsMapper.toEntity(digestsDTO);
        digests = digestsRepository.save(digests);
        return digestsMapper.toDto(digests);
    }

    @Override
    public Optional<DigestsDTO> partialUpdate(DigestsDTO digestsDTO) {
        log.debug("Request to partially update Digests : {}", digestsDTO);

        return digestsRepository
            .findById(digestsDTO.getId())
            .map(
                existingDigests -> {
                    digestsMapper.partialUpdate(existingDigests, digestsDTO);

                    return existingDigests;
                }
            )
            .map(digestsRepository::save)
            .map(digestsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DigestsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Digests");
        return digestsRepository.findAll(pageable).map(digestsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DigestsDTO> findOne(Long id) {
        log.debug("Request to get Digests : {}", id);
        return digestsRepository.findById(id).map(digestsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Digests : {}", id);
        digestsRepository.deleteById(id);
    }
}
