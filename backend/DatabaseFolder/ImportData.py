# import pandas as pd
# import psycopg2
#
# db_config = {
#     'user': 'postgres',
#     'password': 'user1234',
#     'host': 'localhost',
#     'port': '5432',
#     'database': 'CMS'
# }
#
# excel_file_path = 'DataFile.xlsx'
#
# conn = psycopg2.connect(**db_config)
# cursor = conn.cursor()
#
# # Function to process and insert data
# def process_and_insert(sheet_name, column_names):
#     df = pd.read_excel(excel_file_path, sheet_name)
#     df = df.fillna("")
#     df.columns = column_names
#
#     lowercase_columns = [col.lower().replace(' ', '_') for col in column_names]
#     formatted_sheet_name = sheet_name.lower().rstrip('s')
#
#     create_table_query = f"""
#         CREATE TABLE IF NOT EXISTS {sheet_name} (
#             {', '.join([f"{col} VARCHAR" for col in lowercase_columns])},
#             {formatted_sheet_name}_id SERIAL PRIMARY KEY
#         )
#     """
#     cursor.execute(create_table_query)
#     conn.commit()
#
#     for idx, row in df.iterrows():
#         select_query = f"SELECT * FROM {sheet_name} WHERE {lowercase_columns[0]} = %s"
#         cursor.execute(select_query, (row[column_names[0]],))
#         if not cursor.fetchone():  # Check if the record doesn't exist
#             insert_query = f"""
#                 INSERT INTO {sheet_name} ({', '.join(lowercase_columns)}, {formatted_sheet_name}_id)
#                 VALUES ({', '.join(['%s'] * len(column_names))}, %s)
#             """
#             values = tuple([row[col] for col in column_names] + [idx + 1])  # Adding row number as ID
#             cursor.execute(insert_query, values)
#             conn.commit()
#
#
# modules_columns = ['module_code', 'module_name', 'module_description', 'module_level', 'module_credits']
# process_and_insert('Modules', modules_columns)
#
# courses_columns = ['course_code', 'course_name', 'course_description', 'course_level', 'course_credits']
# process_and_insert('Courses', courses_columns)
#
# courses_modules_df = pd.read_excel(excel_file_path, "Courses_Modules")
# courses_modules_df = courses_modules_df.fillna("")
#
# # Get unique module names
# module_columns = courses_modules_df.columns[1:]
# unique_modules = set(module_columns)
#
# # Create a table to store the relationship between courses and modules
# courses_modules_table_query = """
#     CREATE TABLE IF NOT EXISTS courses_modules (
#         course_code VARCHAR,
#         course_id   INTEGER,
#         module_code VARCHAR,
#         module_id   INTEGER
#     )
# """
# cursor.execute(courses_modules_table_query)
# conn.commit()
#
# # Establish relationships between courses and modules
# for index, row in courses_modules_df.iterrows():
#     course_name = row["Course Name"]
#     module_names = list(row.index)[1:]
#
#     select_course_code_query = "SELECT course_code, course_id FROM Courses WHERE course_name = %s"
#     cursor.execute(select_course_code_query, (course_name,))
#     course_row = cursor.fetchone()
#
#     if course_row and not pd.isnull(course_row[0]):
#         course_code, course_id = course_row
#
#         for module_name in module_names:
#             if module_name:
#                 module_value = row[module_name].strip()
#
#                 if module_value:
#                     select_module_code_query = "SELECT module_code, module_id FROM Modules WHERE module_name = %s"
#                     cursor.execute(select_module_code_query, (module_value,))
#                     module_row = cursor.fetchone()
#
#                     if module_row and not pd.isnull(module_row[0]):
#                         module_code, module_id = module_row
#
#                         # Check if the entry already exists
#                         check_query = "SELECT * FROM courses_modules WHERE course_code = %s AND course_id = %s AND module_code = %s AND module_id = %s"
#                         cursor.execute(check_query, (course_code, course_id, module_code, module_id))
#                         existing_entry = cursor.fetchone()
#
#                         if not existing_entry:
#                             insert_query = "INSERT INTO courses_modules (course_code, module_code, course_id, module_id) VALUES (%s, %s, %s, %s)"
#                             insert_values = (course_code, module_code, course_id, module_id)
#                             cursor.execute(insert_query, insert_values)
#                             conn.commit()
#
# cursor.close()
# conn.close()
#
# print("All Data Entity To The Database is Done!")


import pandas as pd
import psycopg2

db_config = {
    'user': 'postgres',
    'password': 'user1234',
    'host': 'localhost',
    'port': '5432',
    'database': 'CMS'
}

excel_file_path = 'DataFile.xlsx'

conn = psycopg2.connect(**db_config)
cursor = conn.cursor()

# Function to process and insert data
def process_and_insert(sheet_name, column_names):
    df = pd.read_excel(excel_file_path, sheet_name)
    df = df.fillna("")
    df.columns = column_names

    lowercase_columns = [col.lower().replace(' ', '_') for col in column_names]
    formatted_sheet_name = sheet_name.lower().rstrip('s')

    create_table_query = f"""
        CREATE TABLE IF NOT EXISTS {sheet_name} (
            {', '.join([f"{col} VARCHAR" for col in lowercase_columns])},
            {formatted_sheet_name}_id SERIAL PRIMARY KEY
        )
    """
    cursor.execute(create_table_query)
    conn.commit()

    for idx, row in df.iterrows():
        select_query = f"SELECT * FROM {sheet_name} WHERE {lowercase_columns[0]} = %s"
        cursor.execute(select_query, (row[column_names[0]],))
        if not cursor.fetchone():  # Check if the record doesn't exist
            insert_query = f"""
                INSERT INTO {sheet_name} ({', '.join(lowercase_columns)}, {formatted_sheet_name}_id) 
                VALUES ({', '.join(['%s'] * len(column_names))}, %s::int)
            """
            values = tuple([row[col] for col in column_names] + [idx + 1])  # Adding row number as ID
            cursor.execute(insert_query, values)
            conn.commit()


modules_columns = ['module_code', 'module_name', 'module_description', 'module_level', 'module_credits', 'module_published']
process_and_insert('Modules', modules_columns)

courses_columns = ['course_code', 'course_name', 'course_description', 'course_level', 'course_credits', 'course_published']
process_and_insert('Courses', courses_columns)

courses_modules_df = pd.read_excel(excel_file_path, "Courses_Modules")
courses_modules_df = courses_modules_df.fillna("")

# Get unique module names
module_columns = courses_modules_df.columns[1:]
unique_modules = set(module_columns)

# Create a table to store the relationship between courses and modules
courses_modules_table_query = """
    CREATE TABLE IF NOT EXISTS courses_modules (
        course_code VARCHAR,
        course_id   INTEGER,
        module_code VARCHAR,
        module_id   INTEGER
    )
"""
cursor.execute(courses_modules_table_query)
conn.commit()

# Establish relationships between courses and modules
for index, row in courses_modules_df.iterrows():
    course_name = row["Course Name"]
    module_names = list(row.index)[1:]

    select_course_code_query = "SELECT course_code, course_id FROM Courses WHERE course_name = %s"
    cursor.execute(select_course_code_query, (course_name,))
    course_row = cursor.fetchone()

    if course_row and not pd.isnull(course_row[0]):
        course_code, course_id = course_row

        for module_name in module_names:
            if module_name:
                module_value = row[module_name].strip()

                if module_value:
                    select_module_code_query = "SELECT module_code, module_id FROM Modules WHERE module_name = %s"
                    cursor.execute(select_module_code_query, (module_value,))
                    module_row = cursor.fetchone()

                    if module_row and not pd.isnull(module_row[0]):
                        module_code, module_id = module_row

                        # Check if the entry already exists
                        check_query = "SELECT * FROM courses_modules WHERE course_code = %s AND course_id = %s AND module_code = %s AND module_id = %s"
                        cursor.execute(check_query, (course_code, course_id, module_code, module_id))
                        existing_entry = cursor.fetchone()

                        if not existing_entry:
                            insert_query = "INSERT INTO courses_modules (course_code, module_code, course_id, module_id) VALUES (%s, %s, %s, %s)"
                            insert_values = (course_code, module_code, course_id, module_id)
                            cursor.execute(insert_query, insert_values)
                            conn.commit()

cursor.close()
conn.close()

print("All Data Entity To The Database is Done!")
