'use strict';

describe('Controller Tests', function() {

    describe('Precio Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPrecio, MockUbicacion, MockCervesa, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPrecio = jasmine.createSpy('MockPrecio');
            MockUbicacion = jasmine.createSpy('MockUbicacion');
            MockCervesa = jasmine.createSpy('MockCervesa');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Precio': MockPrecio,
                'Ubicacion': MockUbicacion,
                'Cervesa': MockCervesa,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("PrecioDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterApp:precioUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
