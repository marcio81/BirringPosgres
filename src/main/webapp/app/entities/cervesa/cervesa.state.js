(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cervesa', {
            parent: 'entity',
            url: '/cervesa',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.cervesa.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cervesa/cervesas.html',
                    controller: 'CervesaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cervesa');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cervesa-detail',{
            parent: 'entity',
            url: '/cervesa/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.cervesa.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cervesa/cervesa-detail.html',
                    controller: 'CervesaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cervesa');
                    $translatePartialLoader.addPart('precio');
                    $translatePartialLoader.addPart('ubicacion');
                    $translatePartialLoader.addPart('comentario');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cervesa', function($stateParams, Cervesa) {
                    return Cervesa.get({id : $stateParams.id});
                }]
            }
        })
        .state('cervesa.new', {
            parent: 'home',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cervesa/cervesa-dialog.html',
                    controller: 'CervesaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('cervesa');
                            return $translate.refresh();
                        }],
                        entity: function () {
                            return {
                                cervesaName: null,
                                tipo: null,
                                fabricante: null,
                                pais: null,
                                graduacion: null,
                                foto: null,
                                fotoContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('home', null, { reload: true });
                }, function() {
                    $state.go('home');
                });
            }]
        })
        .state('cervesa.edit', {
            parent: 'cervesa',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cervesa/cervesa-dialog.html',
                    controller: 'CervesaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cervesa', function(Cervesa) {
                            return Cervesa.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('cervesa', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cervesa.delete', {
            parent: 'cervesa',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cervesa/cervesa-delete-dialog.html',
                    controller: 'CervesaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cervesa', function(Cervesa) {
                            return Cervesa.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('cervesa', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })


        //TOP 10
            .state('top10', {
                parent: 'app',
                url: '/topcervesas',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/top10/Top10.html',
                        controller: 'CervesaController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cervesa');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            
            //Buscadorsad
            .state('busca', {
                parent: 'app',
                url: '/buscacervesas',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/busca/busca.html',
                        controller: 'CervesaController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cervesa');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })



        //Comentarios
/*    .state('comentarios', {
            parent: 'app',
            url: '/comentarios',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cervesa/cervesa-detail.html',
                    controller: 'CervesaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cervesa');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]

            }
        })*/;
    }

})();
