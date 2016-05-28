/**
 * Created by Marcio on 28/05/2016.
 */
(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('busca', {
                parent: 'app',
                url: '/buscacervesas',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/busca/busca.html',
                        controller: 'CtrlBu',
                        controllerAs: 'vm'
                    }
                }
            });
    }
})();
