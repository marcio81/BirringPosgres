(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('evaluar', {
            parent: 'entity',
            url: '/evaluar',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.evaluar.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/evaluar/evaluars.html',
                    controller: 'EvaluarController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('evaluar');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('evaluar-detail', {
            parent: 'entity',
            url: '/evaluar/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.evaluar.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/evaluar/evaluar-detail.html',
                    controller: 'EvaluarDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('evaluar');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Evaluar', function($stateParams, Evaluar) {
                    return Evaluar.get({id : $stateParams.id});
                }]
            }
        })
        .state('evaluar.new', {
            parent: 'evaluar',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluar/evaluar-dialog.html',
                    controller: 'EvaluarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                evaluacion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('evaluar', null, { reload: true });
                }, function() {
                    $state.go('evaluar');
                });
            }]
        })
        .state('evaluar.edit', {
            parent: 'evaluar',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluar/evaluar-dialog.html',
                    controller: 'EvaluarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Evaluar', function(Evaluar) {
                            return Evaluar.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('evaluar', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })

        //Crear evaluacion pasando la id de cerveza y el usuario
            .state('nuevaevaluar', {
                parent: 'home',
                url: '/{idCerveza}/newEvaluar',

                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/evaluar/evaluar-dialog2.html',
                        controller: 'EvaluarDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {

                            entity: ['Cervesa', function(Cervesa) {
                                return {
                                    evaluar: null,
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


        .state('evaluar.delete', {
            parent: 'evaluar',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluar/evaluar-delete-dialog.html',
                    controller: 'EvaluarDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Evaluar', function(Evaluar) {
                            return Evaluar.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('evaluar', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
