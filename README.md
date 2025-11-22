## Introduction ##
- This 'proof of concept' is designed to remove all Permissions other than the VIEW Permission from the Site Member Role, for Documents & Media Folders.
- It will update the Permissions when a Documents & Media Folder is created OR when the Permissions of an existing Documents & Media Folder are updated.
- If the Site Member Role already has the VIEW Permission, it will be retained, but all other Permissions will be removed.
- If the Site Member Role doesn't already have the VIEW Permission then it will NOT be assigned, and ALL Permissions will be removed.

## Implementation ##
- The module contains a ModelListener OSGi module for the ResourcePermission Model with the onBeforeCreate and onBeforeUpdate methods implemented.
- This means the Model Listener is triggered ANYTIME a ResourcePermission is created or updated for ANY type of entity, not just the DLFolder Model.
- It also means the code is triggered when a Documents & Media Folder is created in any Site by any means etc.
- The code checks that the ResourcePermission Name matches DLFolder and that the ResourcePermission RoleId matches the Site Member Role before attempting to make any changes.

## Notes ##
- This is a ‘proof of concept’ that is being provided ‘as is’ without any support coverage or warranty.
- The implementation uses a custom OSGi module meaning it is compatible with Liferay DXP Self-Hosted and Liferay PaaS, but is not compatible with Liferay SaaS.
- The implementation was tested locally using Liferay DXP 2025.Q1.0 LTS.
- JDK 21 is expected for both compile time and runtime.
