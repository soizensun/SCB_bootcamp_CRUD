package support;

import com.digitalacademy.customer.model.response.GetLoanInfoResponse;

public class LoanSupportTest {

    public static GetLoanInfoResponse getLoanMock(){
        GetLoanInfoResponse loanMock = new GetLoanInfoResponse();
        loanMock.setId(1L);
        loanMock.setStatus("OK");
        loanMock.setAccountPayable("102-222-222");
        loanMock.setAccountReceivable("10-232-445-78");
        loanMock.setAmount(3000.0);

        return loanMock;
    }
}
