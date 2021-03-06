package patika.bootcamp.weatherrestservice.controller;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;

import patika.bootcamp.weatherrestservice.exception.ApiError;

//@ResponseBody yazmamak icin:
@RestControllerAdvice
public class RestServiceControllerAdvice extends ResponseEntityExceptionHandler {
		
	//NullPointerException lari yakala onu da metoda parametre olarak gec
	//RestControlerAdvice, NullPointerException i dinliyor
	//NullPointerException alirsan buraya git dedik spring e
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Object> onNullPointerExceptionHandled(NullPointerException e) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "NullPointerException"));
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "HttpMessageNotReadableException"));
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> onRuntimeException(RuntimeException ex){
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage()));
	}

	
	@ExceptionHandler(InternalServerError.class)
	public ResponseEntity<Object> onInternalServerError(InternalServerError error) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "InternalServerError"));
	}
	
	@ExceptionHandler(JsonProcessingException.class)
	public ResponseEntity<Object> onJsonProcessingException(JsonProcessingException e) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "JsonProcessingException"));
	}
	
	@ExceptionHandler(URISyntaxException.class)
	public ResponseEntity<Object> onURISyntaxException(URISyntaxException e){
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "URISyntaxException"));
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "MissingServletRequestParameterException"));
	}
	
	@ExceptionHandler(NotFound.class)
	public ResponseEntity<Object> onNotFound(NotFound error) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "NotFound"));
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> onConstraintViolationException(ConstraintViolationException ex){
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "ConstraintViolationException"));
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> onIllegalArgumentException(IllegalArgumentException e){
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "IllegalArgumentException"));
	}
	
	@ExceptionHandler(MalformedURLException.class)
	public ResponseEntity<Object> onMalformedURLException(MalformedURLException e){
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "MalformedURLException"));
	}
	
	public ResponseEntity<Object> onRestClientException(RestClientException e){
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "RestClientException"));
	}
	
	@ExceptionHandler(BadRequest.class)
	public ResponseEntity<Object> onBadRequest(BadRequest e) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "BadRequest"));
	}
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<Object>(apiError, apiError.getStatus());
	}
	
	
}
