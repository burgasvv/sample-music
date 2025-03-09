package org.burgas.paymentservice.service;

import org.burgas.paymentservice.dto.PaymentResponse;
import org.burgas.paymentservice.mapper.PaymentMapper;
import org.burgas.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(readOnly = true, propagation = REQUIRED)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    public PaymentResponse findById(Long paymentId, String authentication) {
        return paymentRepository
                .findById(paymentId)
                .map(payment -> paymentMapper.toPaymentResponse(payment, authentication))
                .orElseGet(PaymentResponse::new);
    }
}
