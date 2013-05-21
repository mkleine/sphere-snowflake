package forms.paymentForm;

import io.sphere.client.shop.model.PaymentState;
import play.data.validation.Constraints;

public class PaymentNotification {

    @Constraints.Required
    public String transactionId;

    @Constraints.Required
    public String resultCode;

    @Constraints.Required
    public String resultInfo;

    @Constraints.Required
    public String entity;

    @Constraints.Required
    public String statusCode;

    @Constraints.Required
    public String reasonCode;

    @Constraints.Required
    public String longId;

    @Constraints.Required
    public String shortId;

    @Constraints.Required
    public String timestamp;

    @Constraints.Required
    public String network;

    public String referredTransactionPassword;
    public String customerRegistrationId;
    public String customerRegistrationPassword;
    public String amount;
    public String currency;
    public String reference;
    public String interactionCode;
    public String interactionReason;
    public String retryAfter;

    public PaymentNotification() {
    }

    public PaymentState getPaymentState() {
        // TODO Complete it
        if (statusCode.equals("charged")) return PaymentState.Paid;
        if (statusCode.equals("paid_out")) return PaymentState.Paid;
        if (statusCode.equals("pending")) return PaymentState.Pending;
        if (statusCode.equals("failed")) return PaymentState.Failed;
        return PaymentState.Failed;
    }
}