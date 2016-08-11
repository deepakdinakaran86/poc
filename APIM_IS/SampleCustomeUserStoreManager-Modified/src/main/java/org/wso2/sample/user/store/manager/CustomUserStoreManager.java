package org.wso2.sample.user.store.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.carbon.user.api.RealmConfiguration;
import org.wso2.carbon.user.core.claim.ClaimManager;
import org.wso2.carbon.user.core.profile.ProfileConfigurationManager;
import org.wso2.carbon.user.core.jdbc.JDBCUserStoreManager;
import org.wso2.carbon.user.core.ldap.ReadWriteLDAPUserStoreManager;
import org.wso2.carbon.user.core.util.DatabaseUtil;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.user.core.UserRealm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Sample User Store Manager Class
 */
public class CustomUserStoreManager extends ReadWriteLDAPUserStoreManager {

	private static Log log = LogFactory.getLog(CustomUserStoreManager.class);
	
	public static final String GET_USER_ROLES_SQL = "select um_role_permission.um_role_name,um_role_permission.um_permission_id, "
			+ "um_permission.um_resource_id from um_role_permission, um_permission where "
			+ "um_role_permission.um_permission_id=um_permission.um_id and um_role_permission.um_role_name = ?";
	
    public CustomUserStoreManager() {

    }

    public CustomUserStoreManager(RealmConfiguration realmConfig,
            Map<String, Object> properties, ClaimManager claimManager,
            ProfileConfigurationManager profileManager, UserRealm realm,
            Integer tenantId) throws UserStoreException {

    	super(realmConfig, properties, claimManager, profileManager, realm, tenantId);
    }
    
    @Override
    public Map<String, String> getUserPropertyValues(String userName, String[] propertyNames,
            String profileName) throws UserStoreException {
    	log.debug("getUserPropertyValues method in CustomUserStoreManager.......... ");
    	Map<String, String> map = new HashMap<String, String>();
    	map = super.getUserPropertyValues(userName,propertyNames,profileName);
    	String[] userRoles = getRoleListOfUser(userName);
    	Connection dbConnection = null;
    	String sqlStmt = null;
    	PreparedStatement prepStmt = null;
    	ResultSet rs = null;
    	
    	List<Integer> permissionsList = new ArrayList<Integer>();
    	List<String> permissionsNameList = new ArrayList<String>();
    	
    	JSONObject permissionobj = null;
    	JSONArray permissonJSONArray = new JSONArray();
    	Map<String,Object> userPermissionsMap = new HashMap<String,Object>();
    	try {
    		dbConnection = getDBConnection();
    		sqlStmt = GET_USER_ROLES_SQL;
    		String roleName = "";
    		for( int i = 0; i <= userRoles.length - 1; i++) {
    			roleName = userRoles[i];
    			prepStmt = dbConnection.prepareStatement(sqlStmt);
    			prepStmt.setString(1, roleName);
    			rs = prepStmt.executeQuery();
    			
    			while (rs.next()) {
    				roleName = rs.getString(1);
    				int permissionId = rs.getInt(2);
    				permissionsList.add(permissionId);
    				permissionsNameList.add(rs.getString(3));
    				
    			}
    			try {
					permissionobj = new JSONObject();
					permissionobj.put("role", roleName);
					permissionobj.put("permission", permissionsList);
					permissionobj.put("permission", permissionsNameList);
					permissonJSONArray.put(permissionobj);
					userPermissionsMap.put(roleName, permissionsList);
					userPermissionsMap.put(roleName, permissionsNameList);
					permissionsNameList =  new ArrayList<String>();
					permissionsList = new ArrayList<Integer>();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
    		}
    		
            //map.put("USERPERMISSIONS", permissonJSONArray.toString());
    		map.put("USERPERMISSIONS", userPermissionsMap.toString());
    	} catch (SQLException e) {
            throw new UserStoreException(e.getMessage(), e);
        } finally {
            DatabaseUtil.closeAllConnections(dbConnection, rs, prepStmt);
        }	
    	    
    	return map;
    }

    
    private Connection getDBConnection() throws SQLException {
		Connection dbConnection = dataSource.getConnection();
		dbConnection.setAutoCommit(false);
		return dbConnection;
	}
   
   
}
