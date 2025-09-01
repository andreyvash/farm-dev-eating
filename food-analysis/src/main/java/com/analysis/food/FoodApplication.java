package com.analysis.food;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodApplication {

	public static void main(String[] args) {
		int a = 130;
		Integer b = 130;

		System.out.println(a == b);
		Integer x = 5;
		System.out.println(b == x);
		b = 128;
		x = 128;
		System.out.println(b == x);

		String str = "text";
		String str2 = "text";
		String str3 = new String("text");

		System.out.println(str == str2);
		System.out.println(str == str3);
        try {
            handlePayload();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

	}

	public static void handlePayload() throws JsonProcessingException {
		String payload = "{\n" +
				"  \"applicantId\": \"ASDAS!@#\",\n" +
				"  \"candidate\":\n" +
				"    {\n" +
				"      \"firstName\": \"name\",\n" +
				"      \"lastName\": \"lastName\",\n" +
				"      \"email\": \"name@meil.com\"\n" +
				"    },\n" +
				"  \"job\": {\n" +
				"    \"clientId\": 123213\n" +
				"\n" +
				"  },\n" +
				"  \"dateApplied\": \"date\"\n" +
				"\n" +
				"}";

		ObjectMapper mapper = new ObjectMapper();

		Payload payloadModel = mapper.readValue(payload, Payload.class);

		System.out.println(payloadModel.toString());

	}
	public record Payload(String applicantId, Candidate candidate, Job job, String dateApplied){}
	public record Candidate(String firstName, String lastName, String email){}
	public record Job(Integer clientId){}
}
