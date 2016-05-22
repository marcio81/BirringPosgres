/**
 * Created by Víctor on 22/05/2016.
 */



angular.module('jhipsterApp')
    .config(function ($stateProvider,$urlRouterProvider) {
        $urlRouterProvider.otherwise("/");
        $stateProvider
            .state('Top10', {
                url: '/Top10',
                data: {
                    pageTitle: 'Top10'
                },
                views: {
                    'content@': {
                        templateUrl: 'Top10.html',
                        controller: 'CtrlTop'// hace una peticion y te coge los datos de la peticion y
                        // te los pone en el scope
                    }
                }
            })
    });
