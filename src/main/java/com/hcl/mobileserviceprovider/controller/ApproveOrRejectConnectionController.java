package com.hcl.mobileserviceprovider.controller;

import com.hcl.mobileserviceprovider.service.ApproveOrRejectConnectionService;
import com.hcl.mobileserviceprovider.service.dto.ApproveOrRejectConnection;
import com.hcl.mobileserviceprovider.service.dto.ApproveOrRejectConnectionResponse;
import com.hcl.mobileserviceprovider.service.exception.InvalidConnectionIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/connection")
public class ApproveOrRejectConnectionController {

    @Autowired
    private ApproveOrRejectConnectionService approveOrRejectConnectionService;

    @PutMapping("/{id}")
    public ResponseEntity<ApproveOrRejectConnectionResponse> approveOrRejectConnection(@RequestBody ApproveOrRejectConnection approveOrRejectConnection, @PathVariable Long id) throws InvalidConnectionIdException {
        return new ResponseEntity<>(approveOrRejectConnectionService.approveOrRejectConnection(approveOrRejectConnection, id), HttpStatus.OK);
    }
}
