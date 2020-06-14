import json
from sklearn import linear_model
import sys
import numpy as np
   
if __name__ == '__main__':
    
    with open(r'C:\Users\HP\wildfly-11.0.0.Final\bin\data.json') as json_file:
        data = json.load(json_file)
    data = np.array(data)
    
    y=[]
    x=[]
    for d in data:
        y.append(d['price'])
        x.append(np.array([d['year'],d['km'],d['power']]))
    
    x = np.array(x)
    regr = linear_model.LinearRegression()
    regr.fit(x, y)    
    
    print(regr.predict(np.array([int(sys.argv[1]),int(sys.argv[2]),int(sys.argv[3])]).reshape(1, -1))[0])
    