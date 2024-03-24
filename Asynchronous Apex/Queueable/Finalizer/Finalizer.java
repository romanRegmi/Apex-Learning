public class SampleAsyncJob implements Queueable {

public void execute(QueueableContext ctx) {

UdpateAWSFinalizer f = new UdpateAWSFinalizer();
System.attachFinalizer(f);

//Business logic

}

}

public class UdpateAWSFinalizer implements Finalizer {
// Methods in Finalizer
//getRequestId
//getAsyncApexJobId
//getResult
//getException
public void execute(FinalizerContext ctx) {

String reqId = ctx.getRequestId();
String jobId = Id.valueOf(ctx.getAsyncApexJobId());

if (ctx.getResult() == ParentJobResult.SUCCESS) {
System.debug('Parent Queueable (job id: ' + jobId + '): completed successfully!');

} else { // Queueable failed
//provide a counter

}}}



