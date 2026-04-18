// Show Alert Message using Aura Components

public class ShowAlertMessageHandler {
    
    /* Return true if primary contact found otherwise false */
    @AuraEnabled
    public static Boolean checkIfPrimaryContactExist(String opportunityId) {
        try {
            return [SELECT Id FROM OpportunityContactRole WHERE OpportunityId =: opportunityId AND IsPrimary = true].isEmpty() ? false : true;
        }catch(Exception ex) {
            throw new AuraHandledException(ex.getMessage());
        }
    }
}


<aura:component implements="flexipage:availableForRecordHome,force:hasRecordId" access="global" >
    <aura:attribute name="recordId" type="String"/>
    
    <force:recordData aura:id="recordLoader"
                      recordId="{!v.recordId}"
                      fields="StageName"
                      recordUpdated="{!c.handleUpdate}"
                      />
</aura:component>

({
 handleUpdate : function(component, event, helper) {
  let changeType = event.getParams().changeType;
        if(changeType == "CHANGED") {
            let changedFields = event.getParams().changedFields;
            // Check if stage is changed.
            if(changedFields && changedFields.StageName && changedFields.StageName.oldValue != changedFields.StageName.value) {
                helper.checkPrimaryContactOnOpportunity(component);
            }
        }
 }
})

({
 checkPrimaryContactOnOpportunity : function(component) {
  let action = component.get("c.checkIfPrimaryContactExist");
        action.setParams({
            opportunityId : component.get("v.recordId")
        });
        action.setCallback(this, function(response) {
            if(response.getState() === "SUCCESS" && !response.getReturnValue()) {
                this.showToastMessage();
            }else {
                // Handle Error.
            }
        });
        $A.enqueueAction(action);
 },
    
    showToastMessage : function(component, event, helper) {
        var toastEvent = $A.get("e.force:showToast");
        toastEvent.setParams({
            "title": "Warning!",
            "message": "Opportunity is missing primary contact.",
            "type": "warning",
            "mode": "sticky"
        });
        toastEvent.fire();
    }
})


