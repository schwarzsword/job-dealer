//package edu.netcracker.jobdealer.controller;
//
//
//import edu.netcracker.jobdealer.exceptions.*;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.io.IOException;
//
//
//@RestControllerAdvice
//public class ControllerAdvice extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(value = {UsernameNotFoundException.class})
//    protected ResponseEntity<Object> handleUserNotFoundException(UsernameNotFoundException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//    }
//
//    @ExceptionHandler(value = {EmailExistsException.class})
//    protected ResponseEntity<Object> handleEmailExistsException(EmailExistsException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }
//
//    @ExceptionHandler(value = {BadParameterException.class})
//    protected ResponseEntity<Object> handleBadParameterException(BadParameterException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }
//
//    @ExceptionHandler(value = {RuntimeException.class})
//    protected ResponseEntity<Object> handleRuntimeException(RuntimeException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_MODIFIED, request);
//    }
//
//    @ExceptionHandler(value = {AccountNotFoundException.class})
//    protected ResponseEntity<Object> handleAccountNotFoundException(AccountNotFoundException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }
//
//    @ExceptionHandler(value = {AccountAlreadyInUseException.class})
//    protected ResponseEntity<Object> handleAccountAlreadyInUseException(AccountAlreadyInUseException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }
//
//    @ExceptionHandler(value = {ResumeNotFoundException.class})
//    protected ResponseEntity<Object> handleResumeNotFoundException(ResumeNotFoundException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }
//
//    @ExceptionHandler(value = {ResumeAlreadyExistsException.class})
//    protected ResponseEntity<Object> handleResumeAlreadyExistsException(ResumeAlreadyExistsException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }
//
//    @ExceptionHandler(value = {ApplicantNotFoundException.class})
//    protected ResponseEntity<Object> handleApplicantNotFoundException(ApplicantNotFoundException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//    }
//
//    @ExceptionHandler(value = {NotFoundException.class})
//    protected ResponseEntity<Object> handleNotFoundException(NotFoundException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//    }
//
//    @ExceptionHandler(value = {NoPermissionException.class})
//    protected ResponseEntity<Object> handleNoPermissionException(NoPermissionException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
//    }
//
//    @ExceptionHandler(value = {ReviewNotFountException.class})
//    protected ResponseEntity<Object> handleReviewNotFountException(ReviewNotFountException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//    }
//
//    @ExceptionHandler(value = {DoubleVotingException.class})
//    protected ResponseEntity<Object> handleDoubleVotingException(DoubleVotingException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }
//
//    @ExceptionHandler(value = {CompanyNotFoundException.class})
//    protected ResponseEntity<Object> handleCompanyNotFoundException(CompanyNotFoundException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
//    }
//
//    @ExceptionHandler(value = {VacancyNotFoundException.class})
//    protected ResponseEntity<Object> handleVacancyNotFoundException(VacancyNotFoundException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//    }
//
//    @ExceptionHandler(value = {SkillNotFoundException.class})
//    protected ResponseEntity<Object> handleSkillNotFoundException(SkillNotFoundException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//    }
//
//    @ExceptionHandler(value = {AccountByEmailNotFoundException.class})
//    protected ResponseEntity<Object> handleAccountByEmailNotFoundException(AccountByEmailNotFoundException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//    }
//
//    @ExceptionHandler(value = {AccountByIdNotFoundException.class})
//    protected ResponseEntity<Object> handleAccountByIdNotFoundException(AccountByIdNotFoundException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//    }
//
//    @ExceptionHandler(value = {IllegalArgumentException.class})
//    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }
//
//    @ExceptionHandler(value = {AccountIdExistsException.class})
//    protected ResponseEntity<Object> handleAccountIdExistsException(AccountIdExistsException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }
//
//    @ExceptionHandler(value = {IOException.class})
//    protected ResponseEntity<Object> handleIOException(IOException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
//    }
//
//    @ExceptionHandler(value = {MessageNotFoundException.class})
//    protected ResponseEntity<Object> handleMessageNotFoundException(MessageNotFoundException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//    }
//
//    @ExceptionHandler(value = {TaskNotFoundException.class})
//    protected ResponseEntity<Object> handleTaskNotFoundException(TaskNotFoundException exception, WebRequest request) {
//        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//    }
//}
