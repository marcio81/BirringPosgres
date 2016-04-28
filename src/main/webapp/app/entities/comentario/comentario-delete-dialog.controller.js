(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('ComentarioDeleteController',ComentarioDeleteController);

    ComentarioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Comentario'];

    function ComentarioDeleteController($uibModalInstance, entity, Comentario) {
        var vm = this;
        vm.comentario = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Comentario.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
