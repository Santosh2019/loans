package com.eazybytes.loans.controller;


import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.ErrorResponseDto;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.dto.ResponseDto;
import com.eazybytes.loans.service.ILoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(name = "CRUD REST APIs for Loans in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE loan details")
public class LoansController {
    private ILoanService iLoanService;

    public LoansController(ILoanService iLoanService) {
        this.iLoanService = iLoanService;
    }

    @GetMapping("/check")
    public String getMessage() {
        return "server starter up";
    }

    @Operation(summary = "Create Loan REST API", description = "REST API to create new loan inside EazyBank")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "HTTP Status CREATED"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam @Valid @RequestBody
                                                      @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be 10 digits") String mobileNumber) {
        iLoanService.createLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).
                body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }

    @Operation(summary = "Fetch Loan Details REST API", description = "REST API to fetch loan details based on a mobile number")
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    ),
            @ApiResponse(responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoan(@RequestParam @Valid
                                              @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be 10 digits")
                                                      String mobileNumber) {
        LoansDto loansDto = iLoanService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoan(@Valid @RequestBody LoansDto loansDto) {
        boolean isUpdated = iLoanService.updateLoan(loansDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete/{mobileNumber}")
    public ResponseEntity<ResponseDto> deleteLoan(@PathVariable @Valid String mobileNumber) {
        boolean isDelete = iLoanService.deleteLoan(mobileNumber);
        if (isDelete) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
        }
    }

}


