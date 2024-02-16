import subprocess
import os
import signal


def check_server_running(port):
    try:
        output = subprocess.check_output(f"lsof -i :{port} | grep LISTEN", shell=True)
        return len(output) > 0
    except subprocess.CalledProcessError:
        return False


def kill_server_on_port(port):
    try:
        pid = int(subprocess.check_output(f"lsof -t -i:{port}", shell=True))
        os.kill(pid, signal.SIGTERM)
        print(f"Process on port {port} killed.")
    except (subprocess.CalledProcessError, ValueError):
        pass


def start_spring_boot():
    os.chdir(
        '/Users/chotokabbo/Desktop/FYP/FullApplication/CMS_Test/backend')  # Replace with actual path to your backend folder
    subprocess.Popen(['mvn', 'spring-boot:run'])


def start_react():
    os.chdir(
        '/Users/chotokabbo/Desktop/FYP/FullApplication/CMS_Test/frontend')  # Replace with actual path to your frontend folder
    subprocess.Popen(['npm', 'start'])


def start_keycloak():
    os.chdir(
        '/Users/chotokabbo/Desktop/FYP/FullApplication/CMS_Test/keycloak-server')  # Replace with actual path to your Keycloak folder
    # subprocess.Popen(['./standalone.sh'])
    subprocess.Popen(['bin/kc.sh', 'start-dev'])


if __name__ == "__main__":
    spring_boot_port = 8085
    react_port = 3000
    keycloak_port = 8080

    ans = input("Are you want to re-run the servers: Y/n ?")
    if (ans == "Y") or (ans == "y"):
        if check_server_running(spring_boot_port):
            kill_server_on_port(spring_boot_port)
        if check_server_running(react_port):
            kill_server_on_port(react_port)
        if check_server_running(keycloak_port):
            kill_server_on_port(keycloak_port)

        start_spring_boot()
        start_react()
        start_keycloak()
    else:
        if check_server_running(spring_boot_port):
            kill_server_on_port(spring_boot_port)
        if check_server_running(react_port):
            kill_server_on_port(react_port)
        if check_server_running(keycloak_port):
            kill_server_on_port(keycloak_port)