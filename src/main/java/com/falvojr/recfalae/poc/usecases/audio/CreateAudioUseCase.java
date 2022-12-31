package com.falvojr.recfalae.poc.usecases.audio;

import java.io.InputStream;

import com.falvojr.recfalae.poc.entities.audio.gateway.AudioGateway;
import com.falvojr.recfalae.poc.entities.audio.model.Audio;

public record CreateAudioUseCase(AudioGateway audioGateway) {

    public void execute(Input input) {
        var audio = new Audio();
        audio.setName(input.name());
        audio.setType(input.type());
        audio.setContent(input.content());
        audio.setLanguage(input.language());

        this.audioGateway.create(audio);
    }

    public record Input(String name, String type, InputStream content, String language) { }
}
