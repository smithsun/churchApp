package daily_digest.service.impl;

import daily_digest.domain.PublicNotice;
import daily_digest.repository.PublicNoticeRepository;
import daily_digest.service.PublicNoticeService;
import daily_digest.service.dto.PublicNoticeDTO;
import daily_digest.service.mapper.PublicNoticeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PublicNotice}.
 */
@Service
@Transactional
public class PublicNoticeServiceImpl implements PublicNoticeService {

    private final Logger log = LoggerFactory.getLogger(PublicNoticeServiceImpl.class);

    private final PublicNoticeRepository publicNoticeRepository;

    private final PublicNoticeMapper publicNoticeMapper;

    public PublicNoticeServiceImpl(PublicNoticeRepository publicNoticeRepository, PublicNoticeMapper publicNoticeMapper) {
        this.publicNoticeRepository = publicNoticeRepository;
        this.publicNoticeMapper = publicNoticeMapper;
    }

    @Override
    public PublicNoticeDTO save(PublicNoticeDTO publicNoticeDTO) {
        log.debug("Request to save PublicNotice : {}", publicNoticeDTO);
        PublicNotice publicNotice = publicNoticeMapper.toEntity(publicNoticeDTO);
        publicNotice = publicNoticeRepository.save(publicNotice);
        return publicNoticeMapper.toDto(publicNotice);
    }

    @Override
    public Optional<PublicNoticeDTO> partialUpdate(PublicNoticeDTO publicNoticeDTO) {
        log.debug("Request to partially update PublicNotice : {}", publicNoticeDTO);

        return publicNoticeRepository
            .findById(publicNoticeDTO.getId())
            .map(
                existingPublicNotice -> {
                    publicNoticeMapper.partialUpdate(existingPublicNotice, publicNoticeDTO);

                    return existingPublicNotice;
                }
            )
            .map(publicNoticeRepository::save)
            .map(publicNoticeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicNoticeDTO> findAll() {
        log.debug("Request to get all PublicNotices");
        return publicNoticeRepository.findAll().stream().map(publicNoticeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PublicNoticeDTO> findOne(Long id) {
        log.debug("Request to get PublicNotice : {}", id);
        return publicNoticeRepository.findById(id).map(publicNoticeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PublicNotice : {}", id);
        publicNoticeRepository.deleteById(id);
    }
}
