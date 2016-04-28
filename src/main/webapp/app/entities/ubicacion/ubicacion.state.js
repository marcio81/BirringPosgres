(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ubicacion', {
            parent: 'entity',
            url: '/ubicacion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.ubicacion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ubicacion/ubicacions.html',
                    controller: 'UbicacionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ubicacion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ubicacion-detail', {
            parent: 'entity',
            url: '/ubicacion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.ubicacion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ubicacion/ubicacion-detail.html',
                    controller: 'UbicacionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ubicacion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Ubicacion', function($stateParams, Ubicacion) {
                    return Ubicacion.get({id : $stateParams.id});
                }]
            }
        })
        .state('ubicacion.new', {
            parent: 'ubicacion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ubicacion/ubicacion-dialog.html',
                    controller: 'UbicacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ubiName: null,
                                direccion: null,
                                longitud: null,
                                latitud: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ubicacion', null, { reload: true });
                }, function() {
                    $state.go('ubicacion');
                });
            }]
        })
        .state('ubicacion.edit', {
            parent: 'ubicacion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ubicacion/ubicacion-dialog.html',
                    controller: 'UbicacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ubicacion', function(Ubicacion) {
                            return Ubicacion.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('ubicacion', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ubicacion.delete', {
            parent: 'ubicacion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ubicacion/ubicacion-delete-dialog.html',
                    controller: 'UbicacionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ubicacion', function(Ubicacion) {
                            return Ubicacion.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('ubicacion', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
