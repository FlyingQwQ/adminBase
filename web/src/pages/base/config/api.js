import { platform } from "@/config/platform"

let host = platform.host;

let api = {
    login: `${host}/user/login`, // 登录
    register: `${host}/user/register`, // 注册账号
    logout: `${host}/user/logout`, // 注销登录
    getAllUserInfo: `${host}/user/getAllUserInfo`, // 查询所有用户
    deleteUser: `${host}/user/deleteUser`, // 删除账号
    
    findRoles: `${host}/authorize/findRoles`, // 获取指定用户的角色列表
    findAllRoles: `${host}/authorize/findAllRoles`, // 获取所有角色列表
    addRole: `${host}/authorize/addRole`, // 新建角色
    deleteRole: `${host}/authorize/deleteRole`, // 删除角色
    editRole: `${host}/authorize/editRole`, // 编辑角色

    findPermissions: `${host}/authorize/findPermissions`, // 获取指定用户的权限列表
    findAllPermissions: `${host}/authorize/findAllPermissions`, // 获取所有权限列表
    addPermission: `${host}/authorize/addPermission`, // 新建权限
    deletePermission: `${host}/authorize/deletePermission`, // 删除权限
    editPermission: `${host}/authorize/editPermission`, // 修改权限
    
    getAllRoleMenu: `${host}/authorize/getAllRoleMenu`, // 获取角色菜单
    setRoleMenu: `${host}/authorize/setRoleMenu`, // 设置角色菜单

    getUserMenus: `${host}/menu/getUserMenus`, // 获取用户角色菜单列表
    getMenus: `${host}/menu/getAllMenus`, // 获取菜单列表
    modifyMenu: `${host}/menu/modifyMenu`, // 保存菜单
    addMenu: `${host}/menu/addMenu`, // 添加菜单
    deleteMenu: `${host}/menu/deleteMenu`, // 删除菜单

    findAllResourceButton: `${host}/resourcebutton/findAllResourceButton`, // 查询全部资源按钮
    findResourceButtonByMenuId: `${host}/resourcebutton/findResourceButtonByMenuId`, // 通过菜单ID查询资源按钮
    addResourceButtonByMenuId: `${host}/resourcebutton/addResourceButtonByMenuId`, // 给菜单添加资源按钮
    deleteResourceButtonByMenuId: `${host}/resourcebutton/deleteResourceButtonByMenuId`, // 通过菜单ID删除资源按钮
    addResourceButton: `${host}/resourcebutton/addResourceButton`, // 新增资源按钮
    deleteResourceButton: `${host}/resourcebutton/deleteResourceButton`, // 删除资源按钮
    editResourceButton: `${host}/resourcebutton/editResourceButton`, // 编辑资源按钮

    findAllDictionary: `${host}/dictionary/findAllDictionary`, // 查询全部数据字典
    findDictionaryItemByCode: `${host}/dictionary/findDictionaryItemByCode`, // 通过编码查询数据字典项
}

export default api;