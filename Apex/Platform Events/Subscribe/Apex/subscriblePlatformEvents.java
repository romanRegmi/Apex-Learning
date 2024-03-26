public with sharing class subscribePlatformEvent {

public static void afterInsert(List<Order_Detail_e> orderDetailplatformEventList){
List<task> taskList = new List<task>();
User adminUser = [select id from User where Username = 'ankitjain@integrationdemo.com'];
for(Order_Detail_e orderDetail : orderDetailPlatformEventList){
Task taskDetail = new Task();
taskDetail.Subject = 'Platform Event Subscribed Using Trigger';
taskDetail.Description = orderDetail.Order_Number_c;
taskDetail.OwnerId = adminUser.Id;
taskDetail.ActivityDate = date.today(); I
taskList.add(taskDetail);

if(!taskList.isEmpty()){
try {
insert taskList;
} catch (Exception ex) {
System.debug('Task creation failed