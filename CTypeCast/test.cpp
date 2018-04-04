/*
 * TypeCastingTest.cpp
 *
 *  Created on: Feb 19, 2018
 *      Author: yixie
 */

#include <iostream>
#include <string>

using namespace std;

class Shape{
	protected:
		string name;

	public:
		Shape(string s){name=s;}

		void setName(string s){name = s;}
		string getName() const{return name;}

		//pure virtual function
		virtual double getArea() const {return 0;}
};

class Circle : public Shape{
	double radius;
	public:
		Circle(string n, double r):Shape(n){
			radius = r;
		}
		void setRadius(double r){
			radius = r;
		}

		double getRadius() const{
			return radius;
		}
		virtual double getArea() const{
			return 3.14159 * radius*radius;
		}
};

class Car{
	protected:
		string brand;

	public:
		Car(string s){brand=s;}

		void seBrand(string s){brand = s;}
		string getBrand() const{return brand;}
};

int main(){


	Shape *pShape = new Shape("I am a Shape");
	Circle *pCircle = new Circle("I am a Circle", 3);
	Shape *pShapePoly = new Circle("I am a poly_Circle", 4);
	Car *pCar = new Car("Ford");

	cout<<"valid upcast from pCircle to pShape demo:"<<endl;
	Shape *pCircle_Shape = static_cast<Shape*>(pCircle);
	cout<<pCircle_Shape->getName()<<endl;
	cout<<pCircle_Shape->getArea()<<endl;
	//cout<<pCircle_Shape->getRadius()<<endl;
	cout<<endl;

	cout<<"valid downcast from pCircle_Shape to pCircleAgain demo:"<<endl;
	Circle *pCircleAgain = static_cast<Circle*>(pCircle_Shape);
	cout<<pCircleAgain->getName()<<endl;
	cout<<pCircleAgain->getArea()<<endl;
	cout<<pCircleAgain->getRadius()<<endl;
	cout<<endl;

	cout<<"invalid downcast demo:"<<endl;
	Circle *pCircle1 = static_cast<Circle*>(pShape);
	cout<<pCircle1->getName()<<endl;
	cout<<pCircle1->getRadius()<<endl;
	cout<<endl;

	cout<<"invalid downcast with dynamic_cast demo:"<<endl;
	Circle *pCircle2 = dynamic_cast<Circle*>(pShape);
	//Circle *pCircle2 = dynamic_cast<Circle*>(pShapePoly);
	if(pCircle2 != NULL){
		cout<<pCircle2->getName()<<endl;
		cout<<pCircle2->getArea()<<endl;
		cout<<pCircle2->getRadius()<<endl;
	}
	else{
		cout<<"invalid downcast detected!"<<endl;
	}
	cout<<endl;

	cout<<"invalid cast from pCar to pShape demo:"<<endl;
		//Car *pCar_Shape = static_cast<Car*>(pShape);
		Car *pCar_Shape_Dynamic = dynamic_cast<Car*>(pShape);
		if(pCar_Shape_Dynamic){
			cout<<pCar_Shape_Dynamic->getBrand()<<endl;
			cout<<endl;
		}
		else{
			cout<<"invalid cast from pCar to pShape detected."<<endl;
		}

	return 0;
}

