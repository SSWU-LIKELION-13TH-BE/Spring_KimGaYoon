

package com.likelion.session.controller.calculator;

import com.likelion.session.dto.calculator.request.CalculatorAddRequest;
import com.likelion.session.dto.calculator.request.CalculatorMultiplyRequest;
import com.likelion.session.dto.calculator.request.CalculatorSubtractRequest;
import com.likelion.session.dto.calculator.request.CalculatorDivideRequest;
import com.likelion.session.service.calculator.CalculatorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/add")
    public int addTwoNumbers(@RequestBody CalculatorAddRequest request) {
        return calculatorService.add(request.getNumber1(), request.getNumber2());
    }

    @PostMapping("/subtract")
    public int subtractTwoNumbers(@RequestBody CalculatorSubtractRequest request) {
        return calculatorService.subtract(request.getNumber1(), request.getNumber2());
    }

    @PostMapping("/multiply")
    public int multiplyTwoNumbers(@RequestBody CalculatorMultiplyRequest request) {
        return calculatorService.multiply(request.getNumber1(), request.getNumber2());
    }

    @PostMapping("/divide")
    public int divideTwoNumbers(@RequestBody CalculatorDivideRequest request) {
        if (request.getNumber2() == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        } else {
            return calculatorService.divide(request.getNumber1(), request.getNumber2());
        }

    }
}

