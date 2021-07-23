package project.recruitment.utils.mapper;

import project.recruitment.model.dto.CandidateDTO;
import project.recruitment.model.entity.CandidateEntity;

public class CandidateMapper
{
    public static CandidateDTO toDTO(final CandidateEntity candidateEntity)
    {
        return CandidateDTO.builder()
                .id(candidateEntity.getId())
                .firstName(candidateEntity.getFirstName())
                .lastName(candidateEntity.getLastName())
                .email(candidateEntity.getEmail())
                .cityOfLiving(candidateEntity.getCityOfLiving())
                .contactNumber(candidateEntity.getContactNumber())
                .dateOfBirth(candidateEntity.getDateOfBirth())
                .build();
    }

    public static CandidateEntity toEntity(final CandidateDTO candidateDTO)
    {
        return CandidateEntity.builder()
                .id(candidateDTO.getId())
                .firstName(candidateDTO.getFirstName())
                .lastName(candidateDTO.getLastName())
                .email(candidateDTO.getEmail())
                .cityOfLiving(candidateDTO.getCityOfLiving())
                .contactNumber(candidateDTO.getContactNumber())
                .dateOfBirth(candidateDTO.getDateOfBirth())
                .build();
    }
}
