## Introduction ##
- This 'proof of concept' is designed to remove all Permissions other than the VIEW Permission from the Site Member Role, for Documents & Media Folders (i.e. DLFolder model).
- It will update the Permissions when a new Documents & Media Folder is created OR when the Site Member Permissions of an existing Documents & Media Folder are updated.
- If the Site Member Role already has the VIEW Permission, it will be retained, but all other Permissions will be removed.
- If the Site Member Role doesn't already have the VIEW Permission then it will NOT be assigned, and ALL Permissions will be removed.
	- A ResourcePermissions update for a specific Role is only triggered if the permissions for the Role are actually modified.
- **The custom code will NOT remove Permissions assigned using Control Panel > Roles > Site Roles > Site Member > Define Permissions.**

## Implementation ##
- The module contains a ModelListener OSGi module for the ResourcePermission Model with the onBeforeCreate and onBeforeUpdate methods implemented.
- This means the Model Listener is triggered ANYTIME a ResourcePermission is created or updated for ANY type of entity, not just the DLFolder Model.
- It also means the code is triggered when a Documents & Media Folder is created in any Site by any means etc.
- The code checks that the ResourcePermission Name matches DLFolder and that the ResourcePermission RoleId matches the Site Member Role before attempting to make any changes.

## Notes ##
- This is a ‘proof of concept’ that is being provided ‘as is’ without any support coverage or warranty.
- The implementation uses a custom OSGi module meaning it is compatible with Liferay DXP Self-Hosted and Liferay PaaS, but is not compatible with Liferay SaaS.
- The implementation was tested locally using Liferay DXP 2025.Q1.0 LTS in a non-clustered environment, without Remote Live Staging enabled.
- JDK 21 is expected for both compile time and runtime.
- A DLFolder Model Listener is not used as that wouldn't work for this use case.

## Testing ##
- **Ensure the implementation is tested in a clustered environment using Remote Live Staging.**
