(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('ComentarioDetailController', ComentarioDetailController);

    ComentarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Comentario', 'Cervesa', 'User'];

    function ComentarioDetailController($scope, $rootScope, $stateParams, entity, Comentario, Cervesa, User) {
        var vm = this;
        vm.comentario = entity;
        vm.load = function (id) {
            Comentario.get({id: id}, function(result) {
                vm.comentario = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterApp:comentarioUpdate', function(event, result) {
            vm.comentario = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
