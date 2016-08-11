var UserInfo = {

		init:function(userinfo){
			this.roleNames = userinfo.roleNames,
			this.premissions = userinfo.premissions,
			this.userName = userinfo.userName,
			this.tenant = userinfo.tenant,
			this.currentTenant = userinfo.currentTenant,
			this.isSubClientSelected = userinfo.isSubClientSelected
		},

		getRoleNames: function() {
			return this.roleNames;
		},
		getPremissions: function() {
			return this.premissions;
		},
		getUserName: function() {
			return this.userName;
		}, 
		getTenant: function() {
			return this.tenant;
		},
		getCurrentTenant: function() {
			return this.currentTenant;
		},
		getIsSubClientSelected: function() {
			return this.isSubClientSelected;
		},
		getCurrentDomain: function() {
			if (this.isSubClientSelected) {
				domain = this.currentTenant.currentDomain;
			} else {
				domain = this.tenant.currentDomain;
			}
			return domain;
		},
		getCurrentParentDomain: function() {
			if (this.isSubClientSelected) {
				domain = this.currentTenant.domainName;
			} else {
				domain = this.tenant.domainName;
			}
			return domain;
		},
		getCurrentTenantId: function() {
			if (this.isSubClientSelected) {
				tenantId = this.currentTenant.tenantId;
			} else {
				tenantId = this.tenant.tenantId;
			}
			return tenantId;
		},
		hasPermission(pGroup,permission){
			var flag = false;
			if(this.isSuperAdmin() || this.isTenantAdmin()){
				return true;
			}
			var permArray = this.premissions[pGroup];
			if(permArray != undefined){
				$.each(permArray, function(i, obj){
					if(permission == obj){
						flag = true;
					}
				});
			}
			return flag;
		},
		isSuperAdmin:function(){
		var flag = false;
		var role=this.roleNames;	
		var index=role.indexOf("SuperAdmin")
			if(index!=-1){
				flag=true;
			}
		return flag;
		},
		isTenantAdmin:function(){
			var flag = false;
			var role=this.roleNames;	
			var index=role.indexOf("TenantAdmin")
				if(index!=-1){
					flag=true;
				}
			return flag;
			}
};