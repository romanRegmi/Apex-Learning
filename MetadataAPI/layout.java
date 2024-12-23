MetadataService.MetadataPort service = createService();


private static MetadataService.MetadataPort createService() {
    MetadataService.MetadataPort service = new MetadataService.MetadataPort();
    service.SessionHeader = new MetadataService.SessionHeader_element();
    service.SessionHeader.sessionId = UserInfo.getSessionId();
    service.setEndpoint_x(URL.getSalesforceBaseUrl().toExternalForm());
    return service;
}


public void updateLayout(String layoutApiName, String fieldApiName, Boolean addField) {
    // Retrieve the layout from Salesforce
    MetadataService.Layout layout = (MetadataService.Layout) service.readMetadata('Layout', new String[] { layoutApiName }).getRecords()[0];

    // Find the section that contains the field
    MetadataService.LayoutSection layoutSection;
    for (MetadataService.LayoutSection section : layout.layoutSections) {
        for (MetadataService.LayoutColumn column : section.layoutColumns) {
            if (column.layoutItems!= null) {
                for (MetadataService.LayoutItem item : column.layoutItems) {
                    if (item.field!= null && item.field.equals(fieldApiName)) {
                        layoutSection = section;
                        break;
                    }
                }
            }
        }
    }

    // If the layout section was not found, throw an exception
    if (layoutSection == null) {
        throw new LayoutException('Layout section not found for field: ' fieldApiName);
    }

    // Add or remove the field from the layout section
    if (addField) {
        // Create a new layout item for the field
        MetadataService.LayoutItem layoutItem = new MetadataService.LayoutItem();
        layoutItem.field = fieldApiName;
        layoutItem.behavior = 'Readonly';

        // Add the layout item to the layout section
        layoutSection.layoutItems.add(layoutItem);
    } else {
        // Remove the layout item for the field from the layout section
        for (Integer i = 0; i < layoutSection.layoutItems.size(); i++) {
            if (layoutSection.layoutItems[i].field!= null && layoutSection.layoutItems[i].field.equals(fieldApiName)) {
                layoutSection.layoutItems.remove(i);
                break;
            }
        }
    }

    // Update the layout on Salesforce
    service.updateMetadata(new MetadataService.Metadata[] { layout });
}



//Use the method
updateLayout('Account-Account Layout', 'Name', true);


updateLayout('Account-Account Layout', 'Name', false);
