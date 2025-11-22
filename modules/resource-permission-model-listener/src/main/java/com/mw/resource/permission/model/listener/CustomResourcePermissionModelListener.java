package com.mw.resource.permission.model.listener;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael Wall
 */
@Component(
		immediate = true,
	    service = ModelListener.class
)
public class CustomResourcePermissionModelListener extends BaseModelListener<ResourcePermission> {
	
	@Activate
	protected void activate(Map<String, Object> properties) {
		_log.info("Activating...");
		
        _viewFolderResourceAction = _resourceActionLocalService.fetchResourceAction(DLFolder.class.getName(), ActionKeys.VIEW);
        
		if (Validator.isNotNull(_viewFolderResourceAction)) {        	
			_log.info("DLFolder VIEW action found.");
		} else {
			_log.info("DLFolder VIEW action not found, no changes will be made.");
		}
		
        _log.info("Activated...");
	}
	
    @Override
    public void onBeforeCreate(ResourcePermission resourcePermission) throws ModelListenerException {
    	updateDLFolderSiteMemberPermissions(resourcePermission);
    }
    
    @Override
    public void onBeforeUpdate(ResourcePermission originalResourcePermission, ResourcePermission resourcePermission) throws ModelListenerException {
    	updateDLFolderSiteMemberPermissions(resourcePermission);
    }

    public void updateDLFolderSiteMemberPermissions(ResourcePermission resourcePermission) {
    	        
		if (Validator.isNull(_viewFolderResourceAction)) {        	
			_log.info("DLFolder VIEW action not found, no changes will be made.");
			
			return;
		}
    	
    	long companyId = resourcePermission.getCompanyId();
    	
        Role siteMemberRole = _roleLocalService.fetchRole(companyId, RoleConstants.SITE_MEMBER);
        
        if (Validator.isNull(siteMemberRole)) {        	
        	_log.info("SiteMember role not found, no changes will be made.");
        	
        	return;
        }   
        
        // Only run for DLFolder and Site Member role.
        if (!resourcePermission.getName().equalsIgnoreCase(DLFolder.class.getName())) return;
        if (resourcePermission.getRoleId() != siteMemberRole.getRoleId()) return;
        
        _log.info("Matched DLFolder Site Member role...");
        
        //Set to VIEW permission only
        resourcePermission.setActionIds(_viewFolderResourceAction.getBitwiseValue());
        
        _log.info("Done...");
    }
	
	@Reference
    private ResourceActionLocalService _resourceActionLocalService;

    @Reference
    private RoleLocalService _roleLocalService;
    
    private ResourceAction _viewFolderResourceAction;

	private static final Log _log = LogFactoryUtil.getLog(CustomResourcePermissionModelListener.class);
}