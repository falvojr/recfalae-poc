package com.falvojr.recfalae.poc.config.audio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.falvojr.recfalae.poc.entities.audio.gateway.AudioGateway;
import com.falvojr.recfalae.poc.usecases.audio.CreateAudioUseCase;

@Configuration
public class AudioConfig {

	@Bean
	private CreateAudioUseCase createAudioUseCaseBean(AudioGateway audioGateway) {
		return new CreateAudioUseCase(audioGateway);
	}
}
