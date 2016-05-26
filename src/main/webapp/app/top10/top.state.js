(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('top10', {
            parent: 'app',
            url: '/topcervesas',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/top10/Top10.html',
                    controller: 'CtrlTop',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
