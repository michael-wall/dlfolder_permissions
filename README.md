## Introduction ##
- The Site Member role is assigned the following default Permissions on Documents & Media Folders (when Permissions > Viewable By > Anyone or Site Member is selected):
	- Add Subfolder
	- Add Shortcut
	- Add Document
	- Subscribe
 	- View
- The set of Permissions can be modified when a new Folder is being created by editing the settings in the Permissions section of the New Folder screen, or they can be updated for existing Folders using the Permissions action.
	- However this approach relies on the user remembering to make the Permission changes, meaning it error prone.
- There is no out of the box configuration in recent versions of Liferay DXP to override the defaults e.g. to remove one or more of the default Permissions.
- This 'proof of concept' is designed to remove all Permissions other than the VIEW Permission from the Site Member Role for Documents & Media Folders (i.e. DLFolder model).
- It will update the Permissions when a new Documents & Media Folder is created OR when the Site Member > Permissions of an existing Documents & Media Folder are updated.
	- If the Site Member Role already has the VIEW Permission, it will be retained, but all other Permissions will be removed.
	- If the Site Member Role doesn't already have the VIEW Permission then it will NOT be assigned, and ALL Permissions will be removed.
	- Note that a ResourcePermissions update for a specific Role is only triggered if the permissions for the Role are actually modified. Opening the Permissions screen and clicking Save will not trigger a ResourcePermissions update.
- **Note that this custom module CANNOT remove Permissions assigned using Control Panel > Roles > Site Roles > Site Member > Define Permissions.**

## Implementation ##
- The module contains a ModelListener OSGi module for the ResourcePermission Model with the onBeforeCreate and onBeforeUpdate methods implemented.
- This means the Model Listener is triggered ANYTIME a ResourcePermission is created or updated for ANY type of entity, not just the DLFolder Model.
	- The code checks that the ResourcePermission Name matches DLFolder and that the ResourcePermission RoleId matches the Site Member Role before attempting to make any changes to the ResourcePermission record.
	- It also means the code is triggered when a Documents & Media Folder is created in any Site by any means etc.

## Notes ##
- This is a ‘proof of concept’ that is being provided ‘as is’ without any support coverage or warranty.
- The implementation uses a custom OSGi module meaning it is compatible with Liferay DXP Self-Hosted and Liferay PaaS, but is not compatible with Liferay SaaS.
- The implementation was tested locally using Liferay DXP 2025.Q1.0 LTS in a non-clustered environment, without Remote Live Staging enabled.
- JDK 21 is expected for both compile time and runtime.
- A DLFolder Model Listener is not used as that wouldn't work for this use case.

## Testing ##
- **Ensure the module is fully tested in a clustered environment using Remote Live Staging.**
