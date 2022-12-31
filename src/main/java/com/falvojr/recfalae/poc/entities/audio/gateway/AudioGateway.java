package com.falvojr.recfalae.poc.entities.audio.gateway;

import java.util.Optional;

import com.falvojr.recfalae.poc.entities.audio.model.Audio;

public interface AudioGateway {

	Optional<Audio> findById(String id);

	Optional<Audio> findByFileName(String fileName);
	
	Audio create(Audio model);
	
	Audio update(Audio model);
	
	void delete(String id);
}
