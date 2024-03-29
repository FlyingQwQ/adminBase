
let router = [
    {
        path: 'changePassword',
        component: () => import('@/pages/base/main/ChangePassword.vue')
    },
    // ===============用户管理===============
    {
        path: 'accountManage',
        component: () => import('@/pages/base/userManage/AccountManage.vue')
    },
    {
        path: 'accountEdit',
        component: () => import('@/pages/base/userManage/AccountEdit.vue')
    },
    {
        path: 'permissionManage',
        component: () => import('@/pages/base/userManage/PermissionManage.vue')
    },
    {
        path: 'roleManage',
        component: () => import('@/pages/base/userManage/RoleManage.vue')
    },
    {
        path: 'roleMenuManage',
        component: () => import('@/pages/base/userManage/RoleMenuManage.vue')
    },
    {
        path: 'rolePermissionManage',
        component: () => import('@/pages/base/userManage/RolePermissionManage.vue')
    },

    // ===============系统管理===============
    {
        path: 'menuConfig',
        component: () => import('@/pages/base/systemManage/MenuConfig.vue')
    },
    {
        path: 'dictionary',
        component: () => import('@/pages/base/systemManage/Dictionary.vue')
    },
    {
        path: 'dictionaryList',
        component: () => import('@/pages/base/systemManage/DictionaryList.vue')
    },
    {
        path: 'serviceMonitor',
        component: () => import('@/pages/base/systemManage/ServiceMonitor.vue')
    },
    {
        path: 'resourceButton',
        component: () => import('@/pages/base/systemManage/ResourceButtonManage.vue')
    },
    {
        path: 'filesManage',
        component: () => import('@/pages/base/systemManage/FilesManage.vue')
    },
    
]

export default router;