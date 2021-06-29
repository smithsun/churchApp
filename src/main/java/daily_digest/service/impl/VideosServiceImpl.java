package daily_digest.service.impl;

import daily_digest.domain.Videos;
import daily_digest.repository.VideosRepository;
import daily_digest.service.VideosService;
import daily_digest.service.dto.VideosDTO;
import daily_digest.service.mapper.VideosMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Videos}.
 */
@Service
@Transactional
public class VideosServiceImpl implements VideosService {

    private final Logger log = LoggerFactory.getLogger(VideosServiceImpl.class);

    private final VideosRepository videosRepository;

    private final VideosMapper videosMapper;

    public VideosServiceImpl(VideosRepository videosRepository, VideosMapper videosMapper) {
        this.videosRepository = videosRepository;
        this.videosMapper = videosMapper;
    }

    @Override
    public VideosDTO save(VideosDTO videosDTO) {
        log.debug("Request to save Videos : {}", videosDTO);
        Videos videos = videosMapper.toEntity(videosDTO);
        videos = videosRepository.save(videos);
        return videosMapper.toDto(videos);
    }

    @Override
    public Optional<VideosDTO> partialUpdate(VideosDTO videosDTO) {
        log.debug("Request to partially update Videos : {}", videosDTO);

        return videosRepository
            .findById(videosDTO.getId())
            .map(
                existingVideos -> {
                    videosMapper.partialUpdate(existingVideos, videosDTO);

                    return existingVideos;
                }
            )
            .map(videosRepository::save)
            .map(videosMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideosDTO> findAll() {
        log.debug("Request to get all Videos");
        return videosRepository.findAll().stream().map(videosMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VideosDTO> findOne(Long id) {
        log.debug("Request to get Videos : {}", id);
        return videosRepository.findById(id).map(videosMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Videos : {}", id);
        videosRepository.deleteById(id);
    }
}
