package daily_digest.service.impl;

import daily_digest.domain.Publication;
import daily_digest.repository.PublicationRepository;
import daily_digest.service.PublicationService;
import daily_digest.service.dto.PublicationDTO;
import daily_digest.service.mapper.PublicationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Publication}.
 */
@Service
@Transactional
public class PublicationServiceImpl implements PublicationService {

    private final Logger log = LoggerFactory.getLogger(PublicationServiceImpl.class);

    private final PublicationRepository publicationRepository;

    private final PublicationMapper publicationMapper;

    public PublicationServiceImpl(PublicationRepository publicationRepository, PublicationMapper publicationMapper) {
        this.publicationRepository = publicationRepository;
        this.publicationMapper = publicationMapper;
    }

    @Override
    public PublicationDTO save(PublicationDTO publicationDTO) {
        log.debug("Request to save Publication : {}", publicationDTO);
        Publication publication = publicationMapper.toEntity(publicationDTO);
        publication = publicationRepository.save(publication);
        return publicationMapper.toDto(publication);
    }

    @Override
    public Optional<PublicationDTO> partialUpdate(PublicationDTO publicationDTO) {
        log.debug("Request to partially update Publication : {}", publicationDTO);

        return publicationRepository
            .findById(publicationDTO.getId())
            .map(
                existingPublication -> {
                    publicationMapper.partialUpdate(existingPublication, publicationDTO);

                    return existingPublication;
                }
            )
            .map(publicationRepository::save)
            .map(publicationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PublicationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Publications");
        return publicationRepository.findAll(pageable).map(publicationMapper::toDto);
    }

    /**
     *  Get all the publications where Digests is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PublicationDTO> findAllWhereDigestsIsNull() {
        log.debug("Request to get all publications where Digests is null");
        return StreamSupport
            .stream(publicationRepository.findAll().spliterator(), false)
            .filter(publication -> publication.getDigests() == null)
            .map(publicationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the publications where PublicNotice is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PublicationDTO> findAllWherePublicNoticeIsNull() {
        log.debug("Request to get all publications where PublicNotice is null");
        return StreamSupport
            .stream(publicationRepository.findAll().spliterator(), false)
            .filter(publication -> publication.getPublicNotice() == null)
            .map(publicationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the publications where Videos is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PublicationDTO> findAllWhereVideosIsNull() {
        log.debug("Request to get all publications where Videos is null");
        return StreamSupport
            .stream(publicationRepository.findAll().spliterator(), false)
            .filter(publication -> publication.getVideos() == null)
            .map(publicationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PublicationDTO> findOne(Long id) {
        log.debug("Request to get Publication : {}", id);
        return publicationRepository.findById(id).map(publicationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Publication : {}", id);
        publicationRepository.deleteById(id);
    }
}
