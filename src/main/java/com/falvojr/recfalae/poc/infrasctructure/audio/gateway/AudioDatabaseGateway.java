package com.falvojr.recfalae.poc.infrasctructure.audio.gateway;

import java.util.Objects;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.falvojr.recfalae.poc.entities.audio.gateway.AudioGateway;
import com.falvojr.recfalae.poc.entities.audio.model.Audio;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public record AudioDatabaseGateway(GridFsTemplate gridFsTemplate, GridFsOperations gridFsOps) implements AudioGateway {

	private static final String METADATA_LANGUAGE_FIELD = "language";
	private static final String METADATA_TRANSCRIPT_FIELD = "transcript";

	@Override
	public Optional<Audio> findById(String id) {
		final GridFSFile file = this.gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
		return Optional.ofNullable(this.convertFileToModel(file));
	}

	@Override
	public Optional<Audio> findByFileName(String fileName) {
		final GridFSFile file = this.gridFsTemplate.findOne(new Query(Criteria.where("filename").is(fileName)));
		return Optional.ofNullable(this.convertFileToModel(file));
	}

	@Override
	public Audio create(Audio audio) {
		DBObject metadata = new BasicDBObject();
		metadata.put(METADATA_LANGUAGE_FIELD, audio.getTranscript());
		metadata.put(METADATA_LANGUAGE_FIELD, audio.getLanguage());

		ObjectId objectId = this.gridFsTemplate.store(audio.getContent(), audio.getName(), audio.getType(), metadata);
		audio.setId(objectId.toString());

		return audio;
	}

	@Override
	public Audio update(Audio model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	private Audio convertFileToModel(GridFSFile gridFsFile) {
		Audio model = null;
		if (Objects.nonNull(gridFsFile)) {
			model = new Audio();
			model.setId(gridFsFile.getId().toString());
			model.setName(gridFsFile.getFilename());
			model.setLanguage(gridFsFile.getMetadata().getString(METADATA_LANGUAGE_FIELD));
			model.setTranscript(gridFsFile.getMetadata().getString(METADATA_TRANSCRIPT_FIELD));

			GridFsResource gridFsResource = this.gridFsOps.getResource(gridFsFile);
			model.setType(gridFsResource.getContentType());
			model.setContent(gridFsResource.getContent());
		}
		return model;
	}
}
