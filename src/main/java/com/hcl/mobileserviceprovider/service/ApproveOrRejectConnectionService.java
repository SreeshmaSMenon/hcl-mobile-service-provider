package com.hcl.mobileserviceprovider.service;

import com.hcl.mobileserviceprovider.service.dto.ApproveOrRejectConnection;
import com.hcl.mobileserviceprovider.service.dto.ApproveOrRejectConnectionResponse;
import com.hcl.mobileserviceprovider.service.exception.InvalidConnectionIdException;

public interface ApproveOrRejectConnectionService {

    public ApproveOrRejectConnectionResponse approveOrRejectConnection(ApproveOrRejectConnection approveOrRejectConnection, Long id) throws InvalidConnectionIdException;
}
