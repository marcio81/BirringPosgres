'use strict';

describe('Controller Tests', function() {

    describe('Comentario Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockComentario, MockCervesa, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockComentario = jasmine.createSpy('MockComentario');
            MockCervesa = jasmine.createSpy('MockCervesa');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Comentario': MockComentario,
                'Cervesa': MockCervesa,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ComentarioDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterApp:comentarioUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
