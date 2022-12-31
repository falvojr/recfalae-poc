package com.falvojr.recfalae.poc.infrasctructure.audio.controller;

import static org.springframework.http.HttpHeaders.CONTENT_LANGUAGE;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.falvojr.recfalae.poc.usecases.audio.CreateAudioUseCase;
import com.falvojr.recfalae.poc.usecases.audio.CreateAudioUseCase.Input;

@RestController
public record AudioRestController(CreateAudioUseCase createAudioUseCase) {

	@PostMapping("/audio")
	@ResponseStatus(HttpStatus.CREATED)
	public void createAudio(@RequestHeader(CONTENT_LANGUAGE) String language, @RequestParam("file") MultipartFile file)
			throws IOException {
		Input input = new Input(file.getOriginalFilename(), file.getContentType(), file.getInputStream(), language);
		this.createAudioUseCase.execute(input);
	}
	
	@GetMapping("/audio/ping")
	@ResponseStatus(HttpStatus.OK)
	public String ping() {
		return "pong";
	}
}
