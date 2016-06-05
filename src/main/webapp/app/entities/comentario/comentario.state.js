(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('comentario', {
            parent: 'entity',
            url: '/comentario',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.comentario.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comentario/comentarios.html',
                    controller: 'ComentarioController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('comentario');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('comentario-detail', {
            parent: 'entity',
            url: '/comentario/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.comentario.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comentario/comentario-detail.html',
                    controller: 'ComentarioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('comentario');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Comentario', function($stateParams, Comentario) {
                    return Comentario.get({id : $stateParams.id});
                }]
            }
        })
        .state('comentario.new', {
            parent: 'comentario',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentario/comentario-dialog.html',
                    controller: 'ComentarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                comentario: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('comentario', null, { reload: true });
                }, function() {
                    $state.go('comentario');
                });
            }]
        })
        .state('comentario.edit', {
            parent: 'comentario',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentario/comentario-dialog.html',
                    controller: 'ComentarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Comentario', function(Comentario) {
                            return Comentario.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('comentario', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comentario.delete', {
            parent: 'comentario',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentario/comentario-delete-dialog.html',
                    controller: 'ComentarioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Comentario', function(Comentario) {
                            return Comentario.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('comentario', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
            //Crear comentario pasando la id de cerveza y el usuario
            .state('nuevocomentario', {
                parent: 'home',
                url: '/{idCerveza}/newComentario',
                // url: '/{idCerveza}/{idUser}/newComentario',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/comentario/comentario-dialog2.html',
                        controller: 'ComentarioDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                           /* entity: function () {
                                return {
                                    comentario: null,
                                    id: null
                                };
                            }*/
                           /* entity: ['Cervesa','User', function(Cervesa,User) {
                                return {
                                    comentario: null,
                                    id: null,
                                    cervesa: Cervesa.get({id : $stateParams.idCerveza}),
                                    user: User.get({login : $stateParams.idUser})
                                };
                            }]*/
                            entity: ['Cervesa', function(Cervesa) {
                                return {
                                    comentario: null,
                                    id: null,
                                    cervesa: Cervesa.get({id : $stateParams.idCerveza})
                                   
                                };
                            }]
                        }
                    }).result.then(function() {
                        $state.go('home', null, { reload: true });
                    }, function() {
                        $state.go('home');
                    });
                }]
            })
        ;
    }

})();
